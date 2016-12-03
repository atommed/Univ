using System;

namespace Lab3
{
	public interface IDeactivatable
	{
		event Action Deactivate;
	}
}

