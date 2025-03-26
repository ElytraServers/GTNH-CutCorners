package cn.elytra.gtnh.cutcorners.util;

public class MaskUtils {

	/*
	So, I'm writing this before I forget what the heck is this.

	This is a kind of tool to manipulate the values combined into one integer or long value, like
	Special Values of Research Station recipes.

	Its higher 16 bits is for Min Computation, and the lower 16 bits is for Amperage, like:

	0000_0000_0001_0000_0000_0000_0000_1000
	|-       16       -||-       16      -|
	                   ^ splits here

	* The Min Computation part is:
		0000_0000_0001_0000 (decimal 16)
	* The Amperage part is:
		0000_0000_0000_1000 (decimal 8)

	So you are going to have a MaskOperator like:
	* for higher Min Computation:
		new MaskOperator(16, 16)
	* for lower Amperage:
		new MaskOperator(16, 0)

	And changes the Special Values with the provided methods of MaskOperator.

	For example, I want to change the Amperage to 8, I will do:
		specialValue = (new MaskOperator(16, 0)).toValueInt(specialValue, 8);
	And if I want to change the Min Computation to 25, I will do:
		specialValue = (new MaskOperator(16, 16)).toValueInt(specialValue, 25);

	 */
	public static class MaskOperator {

		private final int bitCount;
		private final int bitOffset;

		private final int mask;
		private final int maxValue;

		public MaskOperator(int bitCount, int bitOffset) {
			this.bitCount = bitCount;
			this.bitOffset = bitOffset;

			this.mask = ((1 << (bitCount + bitOffset))) - (1 << (bitOffset));
			this.maxValue = (1 << bitCount) - 1;
		}

		@Override
		public String toString() {
			return "MaskOperator{bits=" + bitCount + ", offset=" + bitOffset + ", mask=" + Long.toString(mask, 2) + "}";
		}

		private int zero(int value) {
			return value & ~mask;
		}

		private int offset(int value) {
			if(value > maxValue) {
				throw new IllegalArgumentException("the value is too big: " + value + " > " + maxValue);
			}
			return value << bitOffset;
		}

		public int setValue(int whole, int part) {
			return (zero(whole) | offset(part));
		}

		public int getValue(int whole) {
			return ((whole & mask) >> bitOffset);
		}
	}

    public static class MaskOperatorLong {

        private final int bitCount;
        private final int bitOffset;

        private final long mask;
        private final long maxValue;

        public MaskOperatorLong(int bitCount, int bitOffset) {
            this.bitCount = bitCount;
            this.bitOffset = bitOffset;

            this.mask = ((1L << (bitCount + bitOffset))) - (1L << (bitOffset));
            this.maxValue = (1L << bitCount) - 1;
        }

        @Override
        public String toString() {
            return "MaskOperatorLong{" + "mask=" + mask + ", bitOffset=" + bitOffset + ", bitCount=" + bitCount + "}";
        }

        private long zero(long value) {
            return value & ~mask;
        }

        private long offset(long value) {
            if(value > maxValue) {
                throw new IllegalArgumentException("the value is too big: " + value + " > " + maxValue);
            }
            return value << bitOffset;
        }

        public long setValue(long whole, int part) {
            return (zero(whole) | offset(part));
        }

        public long getValue(long whole) {
            return ((whole & mask) >> bitOffset);
        }
    }

}
