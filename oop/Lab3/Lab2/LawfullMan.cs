using System;

namespace Lab3
{
	/**
	 * \brief Man who can't break the law
	 */
	[Serializable]
	public class LawfulMan : Man, IMoneyInteractor {
		public override string Name {
			get {
				return "lawful " + base.Name;
			}
		}

		public override void Accept(IMoneyInteractor m){
			m.Visit(this);
		}

		public void Visit(Enterprise e){
			GoToShop(e);
		}

		public void Visit(LawfulMan l){
			Say($"Hi there, {l.OwnName}");
		}
		public void Visit(Bandit b){
			Say($"{b.OwnName} looks fishily!");
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


