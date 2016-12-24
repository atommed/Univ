using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace OOPLab
{
    public class ViewModelBase : INotifyPropertyChanged
    {
        #region Implementation of INotifyPropertyChanged

        public event PropertyChangedEventHandler PropertyChanged;

        protected virtual void OnPropertyChanged(string propertyName)
        {
            OnPropertyChanged(new PropertyChangedEventArgs(propertyName));
        }

        protected virtual void OnPropertyChanged(PropertyChangedEventArgs args)
        {
            PropertyChanged?.Invoke(this, args);
        }

        #endregion
    }

    public class DelegateCommand : ICommand
    {
        public delegate void ICommandOnExecute(object parameter);

        public delegate bool ICommandOnCanExecute(object parameter);

        private ICommandOnExecute _execute;
        private ICommandOnCanExecute _canExecute;

        public DelegateCommand(ICommandOnExecute onExecuteMethod, ICommandOnCanExecute onCanExecuteMethod)
        {
            _execute = onExecuteMethod;
            _canExecute = onCanExecuteMethod;
        }

        #region ICommand Members

        public event EventHandler CanExecuteChanged
        {
            add { CommandManager.RequerySuggested += value; }
            remove { CommandManager.RequerySuggested -= value; }
        }

        public bool CanExecute(object parameter)
        {
            return _canExecute.Invoke(parameter);
        }

        public void Execute(object parameter)
        {
            _execute.Invoke(parameter);
        }

        #endregion
    }

    class SimulatorViewModel : ViewModelBase
    {
        public string Log => LogModel.Instance.Log;
        public SimulatorModel Units => SimulatorModel.Instance;

        private DelegateCommand exitCommand;
        private void OnExit(object p)
        {
            SimulatorModel.Instance.SaveStateXML();
        }
        public ICommand ExitCommand => exitCommand ?? (exitCommand = new DelegateCommand(OnExit, (p) => true));

        private DelegateCommand stepCommand;
        private void OnStep(object o)
        {
            SimulatorModel.Instance.Step();
            OnPropertyChanged("Units");
            OnPropertyChanged("Log");
        }

        public ICommand StepCommand => stepCommand ?? (stepCommand = new DelegateCommand(OnStep, (p) => true));
    }
}
