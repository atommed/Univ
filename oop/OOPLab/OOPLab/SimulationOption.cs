using System;

namespace OOPLab
{
	[Flags]
	public enum SimulationOption
	{
		NONE = 0,
		ENABLE_CRYSIS = 1<<0,
		ENABLE_TRACKING = 1<<1,
		ENABLE_BANDITS = 1<<2,
		ENABLE_CORPORATIONS = 1<<3,
		ENABLE_LAWFULLS = 1 <<4
	}
}

