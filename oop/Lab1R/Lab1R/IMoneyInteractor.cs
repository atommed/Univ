using System;

namespace Lab1R
{
	public interface IMoneyInteractor
	{
		void Visit(LawfullMan l);
		void Visit(Bandit b);
		void Visit(Enterprise e);
	}
}

