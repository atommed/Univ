using System;

namespace OOPLab
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
			LogModel.Instance.WriteLine ("############# NEXT DAY COMES! ###############");
			SimulatorModel.Instance.Step ();
		}

		public static void Step(){
			var k = Console.ReadKey ().Key;
			if (k == ConsoleKey.N)
				NextDay ();
            else if (k == ConsoleKey.S) {
				for (int i = 0; i < 3000; i++) {
					NextDay ();
				}
			} else if (k == ConsoleKey.X) {
				SimulatorModel.Instance.SaveStateXML ();
			} else if (k == ConsoleKey.C) {
				SimulatorModel.Instance.LoadStateXML ();
			} else if (k == ConsoleKey.B) {
				SimulatorModel.Instance.SaveStateBIN ();
			} else if (k == ConsoleKey.V) {
				SimulatorModel.Instance.LoadStateBIN ();
			} else throw new BadInput (k);
		}
	}
}
