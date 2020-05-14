package Kitchen;

public class Ingredient {
	private String Name;
	private Integer Amount;
	private Boolean Chopped;
	public Ingredient(String name,Integer amount,Boolean chopped)
	{
		Name=name;
		Amount=amount;
		Chopped=chopped;
	}
	
	public Integer getAmount()
	{
		return Amount;
	}
	
	public void setAmount(Integer amount)
	{
		Amount=amount;
	}
	public Boolean getChopped()
	{
		return Chopped;
	}
	public String getName()
	{
		return Name;
	}
}
