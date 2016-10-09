using System;

namespace Lab1R
{
	public class Bandit : Man {
		public override string Name {
			get {
				return "bandit " + base.Name;
			}
		}
		public override void Accept(IMoneyInteractor m){
			m.Visit(this);
		}

		public void Visit(LawfullMan l){
			l.Pay(this, 42);
		}
		public void Visit(Bandit b){
			
		}
		public void Visit(Enterprise e){
			Pay(e, 42);
		}

		protected override void OnMoneyEnd(EconomicUnit taker, decimal amount){
		}
	}
}

