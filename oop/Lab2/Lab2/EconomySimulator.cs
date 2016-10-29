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
		public delegate List<EconomicUnit> GlobalEffect(List<EconomicUnit> all);
		public event GlobalEffect CrysisBegins;
		
		public static EconomySimulator Instance{ get; private set; } 
		static EconomySimulator(){
			Instance = new EconomySimulator ();
		}

		private const int CRYSIS_RAIT = 250;
		private const int BANDIT_DETECT = 10;
		private const double ENTERPRISE_CREATION_RAIT = 16;
		private const double BANDIT_CREATION_RAIT = 4;
		private const double LAWFUL_CREATION_RAIT = 2;

		private Random rnd = new Random();
		private List<EconomicUnit> units;

		public void TryTrackBandit (EconomicUnit bandit,EconomicUnit from, decimal amount){
			if(from.Budget + amount > bandit.Budget && rnd.Next (BANDIT_DETECT) == 0) {
				Console.WriteLine ($"{bandit.Name} was caught!");
				units.Remove (bandit);
			}
		}

		public void RegisterUnit(Bandit b){
			b.BeingPayed += TryTrackBandit;
			RegisterUnit ((EconomicUnit)b);
		}

		public void RegisterUnit(EconomicUnit u){
			units.Add (u);
		}

		public void UnregisterUnit(Bandit b){
			b.BeingPayed -= TryTrackBandit;
			UnregisterUnit (b);
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
			if (rnd.Next (CRYSIS_RAIT) == 1) {
				Console.WriteLine ("OMG! CRYSIS BEGINS!!!");
				GlobalEffect ev = CrysisBegins;
				if (ev != null) {
					units = ev.Invoke (units);
				}
			}
			do {
				TryCreateUnit ();
			} while(units.Count < 2);
			int posA = rnd.Next (units.Count);
			int posB = rnd.Next (units.Count - 1);
			if (posA == posB)
				posB = units.Count - 1;
			var visitor = units [posA] as IMoneyInteractor;
			IMoneyInteractable client = units [posB];
			Console.WriteLine ($"{visitor} visits {client}");
			client.Accept (visitor);
		}

		private List<EconomicUnit> StopSomeUnits(List<EconomicUnit> all){
			for (int i = 0; i < all.Count; i++) {
				if (all[i] is Enterprise && rnd.Next() % 5 == 0)
					all.RemoveAt (i);
			}
			SortByBudgetDescending (all);
			return all.GetRange(0, all.Count / 2);
		}

		private static void SortByBudgetDescending(List<EconomicUnit> all){
			all.Sort ((b, a) => a.Budget.CompareTo (b.Budget));
		}

		public void PrintForbes(){
			Console.WriteLine ("Here is the Forbes raiting!");
			SortByBudgetDescending (units);
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
			CrysisBegins += StopSomeUnits;
		}
	}
}

