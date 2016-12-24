using System;

namespace OOPLab
{
	/**
	 * \brief Companies that earn money by exploitation of people
	 */
	[Serializable]
	public class Enterprise : EconomicUnit, IMoneyInteractor
	{
		private const double START_BUDGET_COEF = 1000;
		protected virtual void TryHireWorker(Man m){
			if (rnd.Next (m.Power * 2) > this.Power) {
				LogModel.Instance.WriteLine ($"{Name} hires {m.Name}");
				decimal earned = (decimal)(Math.Log (m.Power + this.Power) * (rnd.NextDouble () - 0.3))*500;
				if (earned < 0 && -earned < Budget) {
					LogModel.Instance.WriteLine ($"Too bad, {m.Name} had to work harder");
					LogModel.Instance.WriteLine ($"{m.Name} now ows ${-earned:0.##} to {Name}");
					m.Pay (this, -earned);
				} else if (earned < 0 && -earned > Budget) {
					OnMoneyEnd (m, 0);
				} else {
					decimal workerPart = earned * m.Power / (Power + m.Power);
					BePayed (m, earned);
					Pay (m, workerPart);
				}
			} else {
				LogModel.Instance.WriteLine ($"Work in {Name} is too big honor for {m.Name}");
			}
		}

		public void Visit(LawfulMan l){
			TryHireWorker(l);
		}

		public void Visit(Bandit b){
			TryHireWorker(b);
		}

		public void Visit(Enterprise e){
			if (e.Budget < this.Budget) {
				LogModel.Instance.WriteLine ($"{Name} SEO thins {e.Name} has wery bad managers");
				if (e.Power * 10 < this.Power && rnd.Next(10) == 1) {
					e.Pay (this, e.Budget);
				}
			} else {
				LogModel.Instance.WriteLine ($"{Name} SEO claims {e.Name} is a company of thiefs!");
				if (rnd.Next (10) == 0) {
					LogModel.Instance.WriteLine ($"Oh my, {Name} SEO was right. All {e.Name} workers go to prison now!");
					DoDeactivate ();
				}
			}
		}
		
		public override void Accept(IMoneyInteractor m){
			m.Visit(this);
		}

		protected override void OnMoneyEnd(EconomicUnit taker, decimal amount){
			taker.BePayed(this, Budget);
			LogModel.Instance.WriteLine ($"{Name} is a bankrupt, lol");
			DoDeactivate ();
		}

		public Enterprise () : base(NameGenerator.NextEnterprise(),START_BUDGET_COEF)
		{
			LogModel.Instance.WriteLine ($"Startup {Name} was created with ${Budget:#.00}!");
		}
	}
}

