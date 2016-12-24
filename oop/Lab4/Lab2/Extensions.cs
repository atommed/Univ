using System;
using System.Collections.Generic;

namespace Lab3
{
	public static class Extensions
	{
		public static R to<R,T>(this T obj, Func<T, R> f){
			return f(obj);
		}
		public static T to<T>(this T obj, Action<T> f){
			f(obj);
			return obj;
		}

		public static void SortDescending<T>(this List<T> l) where T:IComparable<T> {
			l.Sort ((x, y) => y.CompareTo (x)); 
		}
	}
}

