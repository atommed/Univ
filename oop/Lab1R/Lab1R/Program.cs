using System;

namespace Lab1R
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			var l = new LawfullMan ();
			var b = new Bandit ();
			b.Visit (l);
		}
	}
}
