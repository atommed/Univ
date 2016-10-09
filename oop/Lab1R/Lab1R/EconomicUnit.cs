using System;

namespace Lab1R
{
	public 	abstract class EconomicUnit{
		private static ulong genUUID;
		private static Random rnd;

		public int Power { get; private set;}
		public ulong UUID { get; private set;}
		public decimal Budget{ get; private set;}
		public abstract void Accept(IMoneyInteractor m);
		protected abstract void OnMoneyEnd(EconomicUnit recv, decimal amount);
		protected EconomicUnit(){
			this.UUID = genUUID++;
			this.Power = rnd.Next ();
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

