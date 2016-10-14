using System;

namespace Lab1R
{
	/**
	 * \brief Visitor able to make operations with EconomicUnit
	 */
	public interface IMoneyInteractor
	{
		void Visit(LawfullMan l);
		void Visit(Bandit b);
		void Visit(Enterprise e);
	}
}

