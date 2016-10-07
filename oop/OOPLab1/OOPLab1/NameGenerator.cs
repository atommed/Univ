using System;

namespace OOPLab1
{
	public class NameGenerator
	{
		#region Names
		private static readonly string[] names = {
			"Gregory",
			"Mihail",
			"Vlad",
			"Sergei",
			"Niko",
			"Dimon",
			"Tony",
			"Petr",
			"Vitya",
			"Jenia",
			"Oleg",
			"Nazar",
			"Anatolij",
			"James",
			"Ben",
			"Andrei",
			"Ivan",
			"Pedro",
			"John",
			"Max",
			"Kama",
			"ObiVan",
			"Luke",
			"Darth",
			"Magistr",
			"Doge",
			"Pepe",
		};
		private static readonly string[] surnames = {
			"Faustin",
			"Bellik",
			"Frai",
			"Rudenko",
			"Montana",
			"Bond",
			"Pushkin",
			"Laden",
			"Korolev",
			"Gagarin",
			"VyrviGlaz",
			"OrlinijGlaz",
			"Ivanov",
			"Doe",
			"Progulkin",
			"Pupkin",
			"Sveklov",
			"Arbuzov",
			"Payne",
			"Android",
			"The Bullet",
			"The Strong",
			"The Smart",
			"The Fearless",
			"The Frog"
		};
		#endregion

		private static readonly string[] namePairs;
		private static Random rnd = new Random();

		static NameGenerator ()
		{
			namePairs = new string[names.Length * surnames.Length];
			int pos = 0;
			foreach (var name in names)
				foreach (var surname in surnames)
					namePairs [pos++] = name + " " + surname;

		}

		public static string NextName(){
			return namePairs[rnd.Next(0,namePairs.Length)];
		}
	}
}

