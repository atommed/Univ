using System;

namespace Lab2
{
	/**
	 * \brief Man who earns money by breaking the law
	 */
	public class Bandit : Man, IMoneyInteractor {
		public override string Name {
			get {
				return "bandit " + base.Name;
			}
		}
		public override void Accept(IMoneyInteractor m){
			m.Visit(this);
		}

		public void Visit(Enterprise e){
			if ((double)Power / e.Power < 2) {
				GoToShop (e);
			} else {
				Say ($"Robbing {e.Name} will be a big one!");
				int successProbe = rnd.Next(this.Power*2);
				Console.WriteLine($"{Name} tries to rob {e.Name}");
				if (successProbe < e.Power) {
					Console.WriteLine ($"{Name} failed to rob {e.Name}");
					Say ("I had a dream but it will newer happen");
					Die ();
				} else {
					decimal amount = (decimal)(rnd.Next(1,50) / 50.0m * e.Budget * Power / (Power + e.Power));
					e.Pay (this, amount);
					Say ("Yeah!");
				}
			}
		}

		public void Visit(LawfulMan l){
			Console.WriteLine($"{Name} wants to rob {l.Name}");
			if (l.Power / this.Power > 4) {
				Say ($"{l.ownName} is too stronk for me, robbing him is not a good idea");
			} else {
				decimal amount = (decimal)(this.Power / Math.Sqrt(l.Power * 10));
				int successProbe = rnd.Next (15 * this.Power);
				if (successProbe < l.Power) {
					Console.WriteLine ($"{Name} failed to rob {l.ownName}");
					Say ("Hah, maybe in my next life I gonna find myself a better job");
					Die ();
				} else {
					Say ($"Yo, {l.ownName}, u owe me ${amount:0.##}, yo!");
					l.Pay (this, amount);
				}
			}
		}

		public void Visit(Bandit b){
			Say($"It takes a thief to catch a thief, {b.ownName}!");
			if (b.Power / this.Power > 2) {
				Say ($"Don't want to have problems with dat danger man {b.Name}");
			} else {
				if (rnd.Next (30) == 1) {
					Console.WriteLine ($"{Name} fails in robbing {b.Name}");
					Say ("Even the best ones fail sometimes");
					Die ();
				} else {
					Say ("Now you pay me!");
					b.OnMoneyEnd (this, b.Budget);
				}
			}
		}

		protected override void OnMoneyEnd(EconomicUnit taker, decimal amount){
			Console.WriteLine ($"{Name} tries to run!");
			if (rnd.Next (10 * taker.Power) < this.Power) {
				Console.WriteLine ($"{Name} runs away from {taker.Name}");
				Say ("You have to drink more coffe, bro");
			} else {
				Console.WriteLine ($"{Name} fails to run");
				taker.BePayed (this, Budget);
				Say("Omg, have no more money, what for I've wasted my life?");
				Die ();
			}
		}
	}
}

