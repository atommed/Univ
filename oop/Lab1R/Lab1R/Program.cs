using System;

//TODO: Start all methods from capital letter

namespace Lab1R
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			var l = new LawfullMan ();
			var b = new LawfullMan ();
			b.Visit (l);
		}
	}
}
