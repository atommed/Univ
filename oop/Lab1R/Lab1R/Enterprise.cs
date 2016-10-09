using System;

namespace Lab1R
{
	public class Enterprise : EconomicUnit
	{
		public override void Accept(IMoneyInteractor m){
			m.Visit(this);
		}

		protected override void OnMoneyEnd(EconomicUnit taker, decimal amount){
		}

		public Enterprise ()
		{
		}
	}
}

