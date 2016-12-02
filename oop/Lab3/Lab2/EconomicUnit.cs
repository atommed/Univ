using System;
using LNDist = MathNet.Numerics.Distributions.LogNormal;

namespace Lab2
{
	/**
	 * \brief Base class for the objects of real world
	 */
	public 	abstract class EconomicUnit : IMoneyInteractable, IDeactivatable, IComparable<EconomicUnit> {
		public delegate void OnPayment(EconomicUnit recv, EconomicUnit payer, decimal amount);
		public event OnPayment BeingPayed;
		public event DeactivateHandler Deactivate;

		private static ulong genUUID;
		protected static Random rnd = new Random();
		private static LNDist budgetDist = new LNDist(0,0.5);
		public static readonly int MAX_POWER = 10000;


		public int Power { get; private set;}
		public ulong UUID { get; private set;}
		public decimal Budget{ get; private set;}
		public readonly string ownName;
		public virtual string Name {
			get {
				return ownName;
			}
		}

		public abstract void Accept(IMoneyInteractor m);
		protected abstract void OnMoneyEnd(EconomicUnit recv, decimal amount);

		protected EconomicUnit(string name,double budgetCoef = 1){
			if(budgetCoef < 0) throw new ArgumentOutOfRangeException("budgetCoef","Must be > 0");
			this.UUID = genUUID++;
			this.Power = rnd.Next (1,MAX_POWER);
			this.Budget = (decimal)(budgetDist.Sample () * budgetCoef);
			this.ownName = name;
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

