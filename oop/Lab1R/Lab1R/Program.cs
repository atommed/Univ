using System;
//TODO: Start all methods from capital letter

namespace Lab1R
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			while(true) {
				Console.WriteLine ("############# NEXT DAY COMES! ###############");
				EconomySimulator.Instance.Step ();
				var k = Console.ReadKey ().Key;
				if (k == ConsoleKey.Escape)
					break;
				else if (k == ConsoleKey.F) {
					Console.WriteLine ();
					EconomySimulator.Instance.PrintForbes ();
				} else if (k == ConsoleKey.S) {
					for (int i = 0; i < 3000; i++) {
						EconomySimulator.Instance.Step ();
					}
				}
			}
		}
	}
}
