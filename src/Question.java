
public class Question {
	String topic;
	String text;
	String type;
	int weight;
	int answer;
	boolean isAnswered = false;
	
	public Question(String topic, String text, String type, int weight) {
		this.topic = topic;
		this.text = text;
		this.type = type;
		this.weight = weight;
	}
	public void wasAnswered(int answer) {
		isAnswered = true;
		this.answer = answer;
	}
}
