package me.coley.cafedude;

import me.coley.cafedude.classfile.constant.Placeholders;
import me.coley.cafedude.classfile.instruction.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for sized elements.
 */
public class SizeTest implements Opcodes {
	@Nested
	class Instruction {
		@Test
		void testBasicInstruction() {
			// Size is 1 byte (opcode)
			BasicInstruction instruction = new BasicInstruction(NOP);
			assertEquals(1, instruction.computeSize());
		}

		@Test
		void testMultiANewArrayInstruction() {
			// Size is 3 bytes (opcode + cp_index)
			MultiANewArrayInstruction instruction = new MultiANewArrayInstruction(Placeholders.CLASS, 0);
			assertEquals(3, instruction.computeSize());
		}

		@Test
		void testIincInstruction() {
			// Size is 3 bytes (opcode + operand_var + operand_incr)
			IincInstruction instruction = new IincInstruction(1, 1);
			assertEquals(3, instruction.computeSize());
		}

		@Test
		void testWideInstruction() {
			int[] four_byte_ops = {
					LLOAD, DLOAD, LSTORE, DSTORE
			};
			int[] three_byte_ops = {
					RET, ILOAD, FLOAD, ALOAD, ISTORE, FSTORE, ASTORE
			};

			// IINC is 6 bytes
			WideInstruction instruction = new WideInstruction(new IincInstruction(0, 0));
			assertEquals(6, instruction.computeSize());

			// Wide type variable insns are 4
			for (int op : four_byte_ops) {
				instruction = new WideInstruction(new IntOperandInstruction(op, 0));
				assertEquals(4, instruction.computeSize());
			}
			// Normal variable insns are 3
			for (int op : three_byte_ops) {
				instruction = new WideInstruction(new IntOperandInstruction(op, 0));
				assertEquals(3, instruction.computeSize());
			}
		}

		@Test
		void testCpRefInstruction() {
			int[] three_byte_ops = {
					LDC_W,
					LDC2_W,
					GETSTATIC,
					PUTSTATIC,
					GETFIELD,
					PUTFIELD,
					INVOKEVIRTUAL,
					INVOKESPECIAL,
					INVOKESTATIC,
					INVOKEINTERFACE,
					INVOKEDYNAMIC,
					NEW,
					ANEWARRAY,
					CHECKCAST,
					INSTANCEOF
			};

			// Size is 3 bytes (opcode + operand)
			for (int op : three_byte_ops) {
				CpRefInstruction instruction = new CpRefInstruction(op, Placeholders.CONST_REF);
				assertEquals(3, instruction.computeSize());
			}

			// Size is 2 bytes (opcode + operand)
			CpRefInstruction instruction = new CpRefInstruction(LDC, Placeholders.CONST_REF);
			assertEquals(2, instruction.computeSize());
		}

		@Test
		void testIntOperandInstruction() {
			int[] two_byte_ops = {
					RET,
					BIPUSH,
					ALOAD,
					ASTORE,
					DLOAD,
					DSTORE,
					FLOAD,
					FSTORE,
					ILOAD,
					ISTORE,
					LLOAD,
					LSTORE,
					NEWARRAY
			};

			// Size is 2 bytes (opcode + operand)
			for (int op : two_byte_ops) {
				IntOperandInstruction instruction = new IntOperandInstruction(op, 0);
				assertEquals(2, instruction.computeSize());
			}

			int[] three_byte_ops = {
					SIPUSH,
					IFEQ,
					IFNE,
					IFLT,
					IFGE,
					IFGT,
					IFLE,
					IF_ICMPEQ,
					IF_ICMPNE,
					IF_ICMPLT,
					IF_ICMPGE,
					IF_ICMPGT,
					IF_ICMPLE,
					IF_ACMPEQ,
					IF_ACMPNE,
					IFNULL,
					IFNONNULL,
					GOTO,
					JSR
			};

			// Size is 3 bytes (opcode + operand)
			for (int op : three_byte_ops) {
				IntOperandInstruction instruction = new IntOperandInstruction(op, 0);
				assertEquals(3, instruction.computeSize());
			}
		}

		@Test
		void testLookupSwitchInstruction() {
			// Minimal case (empty switch) is 9 bytes:
			//  opcode + default + npairs
			LookupSwitchInstruction instruction = new LookupSwitchInstruction(0, 0, Collections.emptyList(), Collections.emptyList());
			assertEquals(9, instruction.computeSize());

			// Changing the padding will add to the total size
			instruction = new LookupSwitchInstruction(1, 0, Collections.emptyList(), Collections.emptyList());
			assertEquals(9 + 1, instruction.computeSize());

			// Adding one pair entry should add 8 per entry
			instruction = new LookupSwitchInstruction(0, 0, Collections.singletonList(1), Collections.singletonList(1));
			assertEquals(9 + 8, instruction.computeSize());
		}

		@Test
		void testTableSwitchInstruction() {
			// Minimal case (empty switch) is 13 bytes:
			// opcode + default + min + max
			TableSwitchInstruction instruction = new TableSwitchInstruction(0, 0, 0, Collections.emptyList());
			assertEquals(13, instruction.computeSize());

			// Changing the padding will add to the total size
			instruction = new TableSwitchInstruction(0, 0, 0, Collections.emptyList());
			instruction.setPadding(1);
			assertEquals(13 + 1, instruction.computeSize());

			// Adding one pair entry should add 4 per entry
			instruction = new TableSwitchInstruction(0, 0, 0, Collections.singletonList(1));
			assertEquals(13 + 4, instruction.computeSize());
		}
	}

	// TODO: Size tests for other size-bound elements
	//   @Nested class Attribute { }
}
