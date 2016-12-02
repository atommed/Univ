using System;

namespace Lab2
{
	class BadInput: Exception{
		public readonly ConsoleKey key;
		public BadInput(ConsoleKey k) 
			:base($"Can't handle \"{k}\""){
			key = k;
		}
	}

	class Program
	{
		private static void NextDay(){
			Console.WriteLine ("############# NEXT DAY COMES! ###############");
			EconomySimulator.Instance.Step ();
		}

		public static void Step(){
			var k = Console.ReadKey ().Key;
			if (k == ConsoleKey.N)
				NextDay ();
			else if (k == ConsoleKey.F) {
				Console.WriteLine ();
				EconomySimulator.Instance.PrintForbes ();
			} else if (k == ConsoleKey.S) {
				for (int i = 0; i < 3000; i++) {
					NextDay ();
				}
			} else
				throw new BadInput (k);
		}

		public static void Main (string[] args)
		{
			while(true) {
				try{
					Step();
				} catch(BadInput b){
					if (b.key == ConsoleKey.Escape)
						break;
					else {
						Console.WriteLine (b.Message);
						Console.WriteLine ("Use keys Esc to quit, F to print top units, N to step and S to skip");
					}
				}
			}
		}
	}
}
