using System;

namespace Lab2
{
	public delegate void DeactivateHandler();
	public interface IDeactivatable
	{
		event DeactivateHandler Deactivate;
	}
}

