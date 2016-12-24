using System;
using System.Collections.ObjectModel;

namespace OOPLab
{
	[Serializable]
	public class UnitHolder<T> : ObservableCollection<T> where T:IDeactivatable, IComparable<T>
	{
		public void Register (T v)
		{
			if (Contains (v))
				throw new InvalidOperationException ("All units must be unique");
			v.Deactivate += () => Unregister (v);
			Add(v);
		}

		public void Unregister (T v)
		{
			Remove (v);
		}
	}
}

