using System;
using System.Collections.Generic;

namespace Lab3
{
	[Serializable]
	public class UnitHolder<T> : List<T> where T:IDeactivatable, IComparable<T>
	{
		private void InsertSorted (T v)
		{
			if (Count == 0)
				Add (v);
			else {
				int i = 0;
				while (i < Count && v.CompareTo (this [i]) < 0)
					i++;
				Insert (i, v);
			}
		}

		public void Register (T v)
		{
			if (Contains (v))
				throw new InvalidOperationException ("All units must be unique");
			v.Deactivate += () => Unregister (v);
			InsertSorted (v);
		}

		public void Unregister (T v)
		{
			Remove (v);
		}
	}
}

