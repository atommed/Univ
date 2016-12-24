using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OOPLab
{
    class LogModel
    {
        private const int MAX_LOG_LENGTH = 5000;
        public static LogModel Instance { get; private set; }

        static LogModel()
        {
            Instance = new LogModel();
        }
        private LogModel()
        {                        
        }   
        public string Log { get; private set; }

        public void Write(string msg)
        {
            string res = Log + msg;
            if (res.Length > MAX_LOG_LENGTH) res = res.Substring(res.Length - MAX_LOG_LENGTH);
            Log = res;            
        }

        public void WriteLine(string msg)
        {
            Write(msg + '\n');
        }
    }
}
