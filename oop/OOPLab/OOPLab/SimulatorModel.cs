using System;
using System.IO;
using System.Linq;
using System.Diagnostics;
using System.Collections;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Windows.Navigation;

namespace OOPLab
{
	/**
	 * \brief Singleton class for modeling economical situation
	 */
	public class SimulatorModel : IEnumerable<EconomicUnit>
	{
		public static SimulatorModel Instance{ get; private set; }

		static SimulatorModel ()
		{
			Instance = new SimulatorModel ();
		}

		private const int CRYSIS_RAIT = 250;
		private const int BANDIT_DETECT = 10;
		private const double ENTERPRISE_CREATION_RAIT = 16;
		private const double BANDIT_CREATION_RAIT = 4;
		private const double LAWFUL_CREATION_RAIT = 2;
		private readonly SimulationOption simulationOptions;

		private Random rnd = new Random ();
		private UnitHolder<EconomicUnit> registry;

		public EconomicUnit this [string name]{            
			get {
			    return registry.First(u => u.OwnName == name);
			}
		}

		public IEnumerator<EconomicUnit> GetEnumerator(){
			return registry.GetEnumerator ();
		}

		System.Collections.IEnumerator IEnumerable.GetEnumerator(){
			return registry.GetEnumerator ();
		}

		public void RegisterUnit (Bandit b)
		{
			if ((simulationOptions & SimulationOption.ENABLE_TRACKING) != SimulationOption.NONE) {
				b.BeingPayed += delegate(EconomicUnit bandit, EconomicUnit from, decimal amount) {
					if (from.Budget + amount > bandit.Budget && rnd.Next (BANDIT_DETECT) == 0) {
						LogModel.Instance.WriteLine ($"{bandit.Name} was caught!");
						registry.Unregister (bandit);
						return 0;
					} else return amount;
				};
			}
			RegisterUnit ((EconomicUnit)b);
		}

		public void RegisterUnit (EconomicUnit u)
		{
			registry.Register (u);
		}

		private void TryCreateUnit ()
		{
			bool canEnterprise = (simulationOptions & SimulationOption.ENABLE_CORPORATIONS) != SimulationOption.NONE;
			bool canBandit = (simulationOptions & SimulationOption.ENABLE_BANDITS) != SimulationOption.NONE;
			bool canLawfull = (simulationOptions & SimulationOption.ENABLE_LAWFULLS) != SimulationOption.NONE;

			double rait = rnd.NextDouble ();
			if (canEnterprise && rait < 1 / ENTERPRISE_CREATION_RAIT)
				RegisterUnit (new Enterprise ());
			else if (canBandit && rait < 1 / BANDIT_CREATION_RAIT)
				RegisterUnit (new Bandit ());
			else if (canLawfull && rait < 1 / LAWFUL_CREATION_RAIT)
				RegisterUnit (new LawfulMan ());
		}

		public void Step ()
		{
			bool canCrysis = (simulationOptions & SimulationOption.ENABLE_CRYSIS) != SimulationOption.NONE;
			if (canCrysis && rnd.Next (CRYSIS_RAIT) == 1) {
				LogModel.Instance.WriteLine ("OMG! CRYSIS BEGINS!!!");
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
			LogModel.Instance.WriteLine ($"{visitor} visits {client}");
			client.Accept (visitor);
		}

	    private bool predicate(EconomicUnit v)
	    {
            return (v is Enterprise && (rnd.Next(3) == 1) || v is Man && (rnd.Next(15) == 1));
        }

		private void StopSomeUnits ()
		{
		    foreach (var u in registry.Where(predicate)) registry.Remove(u);
			int c = registry.Count;
		    if (c > 2)
		    {
		        decimal averageBudget = registry.Average(u => u.Budget);
		        foreach (var u in registry.Where(u => u.Budget < averageBudget))
		        {
		            registry.Remove(u);
		        }
		    }
		}

		public void SaveStateBIN(){
			var data = registry.ToArray ();
			using (var fs = new FileStream ("data.bin", FileMode.Create)) {
				BinaryFormatter formatter = new BinaryFormatter ();
				formatter.Serialize (fs, data);
			}
		}

		public void LoadStateBIN(){
			if (!File.Exists ("data.bin"))
				return;
			using (var fs = new FileStream ("data.bin", FileMode.Open)) {
				BinaryFormatter fmt = new BinaryFormatter ();
				var data = (EconomicUnit[])fmt.Deserialize (fs);
				registry.Clear ();
				Array.ForEach (data, registry.Register);
			}
		}
			
		public void SaveStateXML(){
			var data = registry.ToArray ();
			XmlSerializer ser = new XmlSerializer(typeof(EconomicUnit[]));
			TextWriter writer = new StreamWriter("data.xml");
			ser.Serialize(writer, data);
			writer.Close();
		}

		public void LoadStateXML(){
			if (!File.Exists ("data.xml"))
				return;
			XmlSerializer serializer = new XmlSerializer(typeof(EconomicUnit[]));
			using (var fs = new FileStream ("data.xml", FileMode.Open)) {
				var data = (EconomicUnit[])serializer.Deserialize (fs);
				registry.Clear ();
				Array.ForEach (data, registry.Register);
			}
		}

		private SimulatorModel ()
		{			
			simulationOptions = 
				SimulationOption.ENABLE_BANDITS |
			SimulationOption.ENABLE_CORPORATIONS |
			SimulationOption.ENABLE_CRYSIS |
			SimulationOption.ENABLE_LAWFULLS |
			SimulationOption.ENABLE_TRACKING;
			var watch = new Stopwatch ();
			watch.Start ();
			registry = new UnitHolder<EconomicUnit> ();
			while (registry.Count < 10)
				TryCreateUnit ();
			watch.Stop ();
			LoadStateBIN();
			var t = watch.Elapsed.TotalMilliseconds;
			LogModel.Instance.WriteLine ($"######## World created in {t}ms ###########");
		}
	}
}

