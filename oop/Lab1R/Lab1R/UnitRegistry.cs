using System;
using System.Collections.Generic;

namespace Lab1R
{
	public sealed class UnitRegistry
	{
		public static UnitRegistry Instance{ get; private set;}
		static UnitRegistry() {
			Instance = new UnitRegistry();
		}

		private List<EconomicUnit> registeredUnits;
		private UnitRegistry ()
		{
			registeredUnits = new List<EconomicUnit> ();
		}
		public void registerUnit(EconomicUnit eu){
			registeredUnits.Add (eu);
		}
		public void unregisterUnit(EconomicUnit eu){
			registeredUnits.Remove (eu);
		}
	}
}