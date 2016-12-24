using System;

namespace Lab3
{
	public interface IDeactivatable : IDisposable
	{
		event Action Deactivate;
	}
}

