using System;

namespace Lab1R
{
	class Program
	{
		private static void NextDay(){
			Console.WriteLine ("############# NEXT DAY COMES! ###############");
			EconomySimulator.Instance.Step ();
		}

		public static void Main (string[] args)
		{
			while(true) {
				NextDay ();
				var k = Console.ReadKey ().Key;
				if (k == ConsoleKey.Escape)
					break;
				else if (k == ConsoleKey.F) {
					Console.WriteLine ();
					EconomySimulator.Instance.PrintForbes ();
				} else if (k == ConsoleKey.S) {
					for (int i = 0; i < 3000; i++) {
						NextDay ();
					}
				}
			}
		}
	}
}
