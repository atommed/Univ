using System;


namespace OOPLab1
{	
	interface IMoneyGrabber{
		void visit(LawfullMan lm);
		void visit(Bandit b);
	}

	interface IMoneyGrabbable{
		void accept(IMoneyGrabber g);
	}
	
	abstract class EconomicUnit{
		private static ulong uid = 0;
		public String name{ get;private set;}

		protected virtual decimal budget{ get; set;}

		protected virtual void recieveMoney(EconomicUnit payer, decimal amount){
			this.budget += amount;
		}
		protected virtual void pay(EconomicUnit recv, decimal amount){
			if (this.budget < amount)
				OnMoneyEnd();
			else {
				this.budget -= amount;
				recv.recieveMoney (this, amount);
			}
		}

		protected abstract void OnMoneyEnd();

		public EconomicUnit(string name){
			this.name = name + " #" + (uid++);
		}
	}

	class Man : EconomicUnit{
		public Man(string name) : base(name){
			Console.WriteLine (this.name + " borns");
		}

		protected virtual void Die(){
			Console.WriteLine (this.name + " dies");
		}

		protected override void OnMoneyEnd(){
			Console.WriteLine (this.name + ": Looks like i have no more money");
			Die();
		}
	}

	class LawfullMan : Man, IMoneyGrabbable{
		public LawfullMan(string name) : base("lawfull " + name){}
		public LawfullMan() : this(NameGenerator.NextName()){}
		public void accept(IMoneyGrabber g){
			g.visit(this);
		}
	}
		
	class Bandit : Man, IMoneyGrabbable, IMoneyGrabber{
		public Bandit(string name) : base("bandit " + name){}
		public Bandit() : base("bandit " + NameGenerator.NextName()){}

		public void accept(IMoneyGrabber g){
			g.visit(this);
		}

		public void visit(LawfullMan l){
		}
		public void visit(Bandit b){
		}
	}
	
	class Lab1
	{
		public static void Main (string[] args)
		{
			MathNet.Numerics.Distributions.Normal nd = new MathNet.Numerics.Distributions.Normal (150, 30);
			int a, b, c;
			a = 0;b = 0; c = 0;
			for (int i = 0; i < 10000; i++){
				double res = nd.Sample();
				if (res < 100)
					a++;
				else if (res > 200)
					b++;
				else
					c++;
			}
			Console.WriteLine (a + " " + b + " " + c);
		}
	}
}
