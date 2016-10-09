using System;

namespace Lab1R
{
	public class LawfullMan : Man, IMoneyInteractor {
		public override string Name {
			get {
				return "lawfull " + base.Name;
			}
		}

		public override void Accept(IMoneyInteractor m){
			m.Visit(this);
		}

		protected override void OnMoneyEnd(EconomicUnit taker, decimal amount){
			Pay(taker, Budget);
			Say("Lol, looks like I have no money now");
			Die ();
		}
	}
}


