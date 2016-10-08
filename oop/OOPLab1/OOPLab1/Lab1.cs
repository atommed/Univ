using System;


namespace OOPLab1
{	
	interface IMoneyGrabber{
		void Visit(LawfullMan lm);
		void Visit(Bandit b);
	}

	interface IMoneyGrabbable{
		void Accept(IMoneyGrabber g);
	}
	
	abstract class EconomicUnit : IMoneyGrabbable{
		private static ulong uid = 0;
		public String Name{ get;private set;}
		public decimal Budget{ get;private set;}

		public abstract void Accept(IMoneyGrabber g);
		public virtual void RecieveMoney(EconomicUnit payer, decimal amount){
			this.Budget += amount;
		}
		public virtual void Pay(EconomicUnit recv, decimal amount){
			if (this.Budget < amount)
				OnMoneyEnd(recv);
			else {
				this.Budget -= amount;
				recv.RecieveMoney (this, amount);
			}
		}
		protected abstract void OnMoneyEnd(EconomicUnit taker);
		protected EconomicUnit(string name){
			this.Name = "#" + (uid++) +" " + name;
		}
	}

	abstract class Man : EconomicUnit{
		protected Man(string name) : base(name){
			Console.WriteLine (this.Name + " borns");
		}

		protected virtual void Die(){
			Console.WriteLine (this.Name + " dies");
		}

		protected override void OnMoneyEnd(EconomicUnit taker){
			Pay (taker, this.Budget);
			Console.WriteLine (this.Name + " says: Looks like i have no more money");
			Die();
		}
	}

	class LawfullMan : Man{
		public LawfullMan(string name) : base("lawfull " + name){}
		public LawfullMan() : this(NameGenerator.NextName()){}
		public override void Accept(IMoneyGrabber g){
			g.Visit(this);
		}
	}
		
	class Bandit : Man, IMoneyGrabber{

		public Bandit(string name) : base("bandit " + name){}
		public Bandit() : base("bandit " + NameGenerator.NextName()){}
		public override void Accept(IMoneyGrabber g){
			g.Visit(this);
		}
		public void Visit(LawfullMan l){
			decimal amount = 42;
			Console.WriteLine(l.Name + " ows " + amount + "$ to " + this.Name);
			l.Pay(this, 42);
		}
		public void Visit(Bandit b){
		}
	}
	
	class Lab1
	{
		public static void Main (string[] args)
		{
			var l = new LawfullMan ();
			var b = new Bandit ();
			b.Visit (l);
		}
	}
}
