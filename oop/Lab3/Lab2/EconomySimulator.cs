using System;
using System.Linq;
using System.Diagnostics;
using System.Collections.Generic;

namespace Lab2
{
	class UnitHolder<T> : List<T> where T:IDeactivatable,IComparable<T> {
		private void InsertSorted(T v){
			if (Count == 0)
				Add (v);
			else {
				int i = 0;
				while (i < Count && v.CompareTo(this [i]) < 0)
					i++;
				Insert(i, v);
			}
		}

		public void Register(T v){
			if (Contains (v))
				throw new InvalidOperationException ("All units must be unique");
			v.Deactivate += () => Unregister (v);
			InsertSorted (v);
		}

		public void Unregister(T v){
			Remove (v);
		}
	}
	
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
		private const int BANDIT_DETECT = 10;
		private const double ENTERPRISE_CREATION_RAIT = 16;
		private const double BANDIT_CREATION_RAIT = 4;
		private const double LAWFUL_CREATION_RAIT = 2;

		private Random rnd = new Random();
		private UnitHolder<EconomicUnit> registry;

		public void TryTrackBandit (EconomicUnit bandit,EconomicUnit from, decimal amount){
			if(from.Budget + amount > bandit.Budget && rnd.Next (BANDIT_DETECT) == 0) {
				Console.WriteLine ($"{bandit.Name} was caught!");
				registry.Unregister (bandit);
			}
		}

		public void RegisterUnit(Bandit b){
			b.BeingPayed += TryTrackBandit;
			RegisterUnit ((EconomicUnit)b);
		}

		public void RegisterUnit(EconomicUnit u){
			registry.Register (u);
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

		private void SortDescending(){
			registry.Sort(((x, y) => y.CompareTo(x)));
		}

		public void Step(){
			if (rnd.Next (CRYSIS_RAIT) == 1) {
				Console.WriteLine ("OMG! CRYSIS BEGINS!!!");
				StopSomeUnits ();

			}
			do {
				TryCreateUnit ();
			} while(registry.Count < 2);
			int posA = rnd.Next (registry.Count);
			int posB = rnd.Next (registry.Count - 1);
			if (posA == posB)
				posB = registry.Count - 1;
			var visitor = registry [posA] as IMoneyInteractor;
			IMoneyInteractable client = registry [posB];
			Console.WriteLine ($"{visitor} visits {client}");
			client.Accept (visitor);
		}

		private void StopSomeUnits(){			
			registry.RemoveAll ((EconomicUnit v) => {
				return 
					(v is Enterprise && rnd.Next(3) == 1) ||
					(v is Man        && rnd.Next(15) == 1);
			});
			int c = registry.Count;
			if(c > 2)
				registry.RemoveRange (c / 2, c / 2 - 1);//Removes half of all.
		}

		public void PrintForbes(){
			SortDescending ();
			Console.WriteLine ("Here is the Forbes raiting!");
			for (int i = 0; i < Math.Min(10, registry.Count); i++) {
				EconomicUnit e = registry [i];
				Console.WriteLine ($"#{i+1}: {e.Name} with ${e.Budget:0.##}");
			}
		}

		private EconomySimulator ()
		{			
			var watch = new Stopwatch();
			watch.Start ();
			registry = new UnitHolder<EconomicUnit> ();
			while (registry.Count < 10)
				TryCreateUnit ();
			watch.Stop ();
			var t = watch.Elapsed.TotalMilliseconds;
			Console.WriteLine ($"######## World created in {t}ms ###########");		
		}
	}
}

