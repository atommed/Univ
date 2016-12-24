using System;
using System.Xml.Serialization;
using LNDist = MathNet.Numerics.Distributions.LogNormal;

namespace Lab3
{
	/**
	 * \brief Base class for the objects of real world
	 */
	[XmlInclude(typeof(Bandit))]
	[XmlInclude(typeof(LawfulMan))]
	[XmlInclude(typeof(Enterprise))]
	[Serializable]
	public 	abstract class EconomicUnit : IMoneyInteractable, IDeactivatable, IComparable<EconomicUnit> {
		[field: NonSerialized] public event Func<EconomicUnit, EconomicUnit, decimal, decimal> BeingPayed;
		[field: NonSerialized] public event Action Deactivate;

		public static ulong genUUID;
		protected static Random rnd = new Random();
		private static LNDist budgetDist = new LNDist(0,0.5);
		public static readonly int MAX_POWER = 10000;


		public int Power { get; set;}
		public ulong UUID { get;}
		public decimal Budget{ get; set;}
		public string OwnName{ get; set;}
		public virtual string Name {
			get {
				return OwnName;
			}
		}

		public abstract void Accept(IMoneyInteractor m);
		protected abstract void OnMoneyEnd(EconomicUnit recv, decimal amount);

		public EconomicUnit(){
		}

		protected EconomicUnit(string name,double budgetCoef = 1){
			if(budgetCoef < 0) throw new ArgumentOutOfRangeException("budgetCoef","Must be > 0");
			this.UUID = genUUID++;
			this.Power = rnd.Next (1,MAX_POWER);
			this.Budget = (decimal)(budgetDist.Sample () * budgetCoef);
			this.OwnName = name;
		}
		public void Pay(EconomicUnit recv, decimal amount){
			if (amount > Budget)
				OnMoneyEnd (recv, amount);
			else {
				this.Budget -= amount;
				recv.BePayed (this,amount);
			}
		}

		protected void DoDeactivate(){
			var tmp = Deactivate;
			if (tmp != null)
				tmp ();
		}

	    public void Dispose()
	    {
	        DoDeactivate();
	    }

		public void BePayed(EconomicUnit from, decimal amount){
			Console.WriteLine ($"{Name} receives {amount:$0.##} from {from.Name}");
			Budget += amount;
			var e = BeingPayed;
			if (e != null) {
				e.Invoke (this, from, amount);
			}
		}

		public int CompareTo(EconomicUnit other){
			return Budget.CompareTo (other.Budget);
		}

		public override string ToString ()
		{
			return $"#{UUID} {Name}";
		}
	}
}

