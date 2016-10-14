﻿using System;

namespace Lab1R
{
	/**
	 * \brief Man who can't break the law
	 */
	public class LawfullMan : Man, IMoneyInteractor {
		public override string Name {
			get {
				return "lawfull " + base.Name;
			}
		}

		public override void Accept(IMoneyInteractor m){
			m.Visit(this);
		}

		public void Visit(Enterprise e){
			GoToShop(e);
		}

		public void Visit(LawfullMan l){
			Say($"Hi there, {l.ownName}");
		}
		public void Visit(Bandit b){
			Say($"{b.ownName} looks fishily!");
			Console.WriteLine($"{b.Name} gets rid of {Name}");
			if (rnd.Next (3) == 1)
				Die ();
			else
				b.Pay (this, b.Budget / 10);
		}

		protected override void OnMoneyEnd(EconomicUnit taker, decimal amount){
			taker.BePayed(this, Budget);
			Say("Lol, looks like I ran out of money");
			Die ();
		}
	}
}


