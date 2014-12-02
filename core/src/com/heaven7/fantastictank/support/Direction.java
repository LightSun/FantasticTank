package com.heaven7.fantastictank.support;

public enum Direction {
	
	Up(1){
		public Direction reverse() {
			return Down;
		}
		@Override
		public String toString() {
			return "Up";
		}
	},
	Down(2){
		public Direction reverse() {
			return Up;
		}
		@Override
		public String toString() {
			return "Down";
		}
	},
	Left(3){
		public Direction reverse() {
			return Right;
		}
		@Override
		public String toString() {
			return "Left";
		}
	},
	Right(4){
		public Direction reverse() {
			return Left;
		}
		@Override
		public String toString() {
			return "Right";
		}
	};

	public final int value;
	
	private Direction(int value){
		this.value = value;
	}
	/**相反方向*/
	public abstract Direction reverse();
}
