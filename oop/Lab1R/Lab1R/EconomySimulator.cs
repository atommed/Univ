using System;
using System.Linq;
using System.Diagnostics;
using System.Collections.Generic;

namespace Lab1R
{
	/**
	 * \brief Singleton class for modeling economical situation
	 */
	public class EconomySimulator
	{
		public static EconomySimulator Instance{ get; private set; } 
		static EconomySimulator(){
			Instance = new EconomySimulator ();
		}

		private const int CRYSIS_RAIT = 250;
		private const double ENTERPRISE_CREATION_RAIT = 16;
		private const double BANDIT_CREATION_RAIT = 4;
		private const double LAWFUL_CREATION_RAIT = 2;

		private Random rnd = new Random();
		private List<EconomicUnit> units;

		public void RegisterUnit(EconomicUnit u){
			units.Add (u);
		}
		public void UnregisterUnit(EconomicUnit u){
			units.Remove (u);
		}

		private void TryCreateUnit(){
			double rait = rnd.NextDouble ();
			if(rait < 1/ENTERPRISE_CREATION_RAIT)
				RegisterUnit(new Enterprise());
			else if(rait < 1/BANDIT_CREATION_RAIT)
				RegisterUnit(new Bandit());
			else if(rait < 1/LAWFUL_CREATION_RAIT)
				RegisterUnit(new LawfulMan());
		}

		public void Step(){
			if (rnd.Next (CRYSIS_RAIT) == 1)
				DoCrysis ();
			do {
				TryCreateUnit ();
			} while(units.Count < 2);
			int posA = rnd.Next (units.Count);
			int posB = rnd.Next (units.Count - 1);
			if (posA == posB)
				posB++;
			EconomicUnit visitor = units [posA];
			EconomicUnit client = units [posB];
			Console.WriteLine ($"{visitor} visits {client}");
			client.Accept (visitor as IMoneyInteractor);
		}

		private void DoCrysis(){
			Console.WriteLine ("OMG! CRYSIS BEGINS!!!");
			for (int i = 0; i < units.Count; i++) {
				if (units [i] is Enterprise && rnd.Next() % 5 == 0)
					units.RemoveAt (i);
			}
			SortByBudgetDescending ();
			units = units.GetRange(0, units.Count / 2);
		}

		private void SortByBudgetDescending(){
			units.Sort ((b, a) => a.Budget.CompareTo (b.Budget));
		}

		public void PrintForbes(){
			Console.WriteLine ("Here is the Forbes raiting!");
			SortByBudgetDescending ();
			for (int i = 0; i < Math.Min(10, units.Count); i++) {
				EconomicUnit e = units [i];
				Console.WriteLine ($"#{i+1}: {e.Name} with ${e.Budget:0.##}");
			}
		}

		private EconomySimulator ()
		{
			var watch = new Stopwatch();
			watch.Start ();
			units = new List<EconomicUnit> ();
			while (units.Count < 10)
				TryCreateUnit ();
			watch.Stop ();
			var t = watch.Elapsed.TotalMilliseconds;
			Console.WriteLine ($"######## World created in {t}ms ###########");
		}
	}
}

