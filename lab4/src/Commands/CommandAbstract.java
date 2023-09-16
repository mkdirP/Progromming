package Commands;

public abstract class CommandAbstract {
    protected String name;
    protected String help;

    public String getName(){
        return name;
    }

    public String getHelp(){
        return help;
    }

    abstract public void execute(CommandManager commandManager,String args[]) throws IOException,ParaInapproException, NullException, ValueTooSmallException,ValueTooSmallException;

}
