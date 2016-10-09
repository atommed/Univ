using System;

namespace Lab1R
{
	public 	abstract class Man : EconomicUnit{
		public virtual string Name{ get; private set;}
		protected virtual void Die(){
			Console.WriteLine ($"{Name} dies");
		}
		protected Man(string name){
			this.Name = name;
			Console.WriteLine ($"{Name} borns");
		}
		protected Man() : this(NameGenerator.NextName()){}
		protected void Say(string what){
			Console.WriteLine ($"{Name} says: {what}");
		}
	}
}

