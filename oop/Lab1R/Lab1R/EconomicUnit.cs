using System;

namespace Lab1R
{
	public 	abstract class EconomicUnit{
		private static ulong genUUID;
		protected static Random rnd = new Random();
		public static readonly int MAX_POWER = 10000;

		public int Power { get; private set;}
		public ulong UUID { get; private set;}
		public decimal Budget{ get; private set;}
		public abstract void Accept(IMoneyInteractor m);
		protected abstract void OnMoneyEnd(EconomicUnit recv, decimal amount);
		protected EconomicUnit(decimal startBudget){
			if(startBudget < 0) throw new ArgumentOutOfRangeException("startBudget","Must be > 0");
			this.UUID = genUUID++;
			this.Power = rnd.Next (MAX_POWER);
			this.Budget = startBudget;
		}
		public void Pay(EconomicUnit recv, decimal amount){
			if (amount > Budget)
				OnMoneyEnd (recv, amount);
			else {
				this.Budget -= amount;
				recv.BePayed (this,amount);
			}
		}
		public void BePayed(EconomicUnit from, decimal amount){
			Budget += amount;
		}
	}
}

