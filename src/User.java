import java.util.ArrayList;

public class User {
	//uses statistical formulas, so 2-4 is 68% of the data, 1.5-4.5 is 95% of the data, and 1-5 is all of the data
	public static final double stDev = 1.0;
	public static final double mean = 3.0;
	
	//total number of points - unadjusted
	double domesticCapitalismScore = 0.0;
	double internationalCapitalismScore = 0.0;
	double interventionismScore = 0.0;
	double socialScore = 0.0;
	
	//to hold answers after adjustment
	double adjustedDCScore;
	double adjustedICScore;
	double adjustedIntervScore;
	double adjustedSocScore;
	
	//standard deviations for each metric
	double dcStD;
	double icStD;
	double intervStD;
	double socStD;
	
	//what someone who answered 3 for each would get
	double moderateDCScore;
	double moderateICScore;
	double moderateIntervScore;
	double moderateSocScore;
	
	int questionsAnswered;
	
	
	
	public User() {}
	
	public void calculateModerateScores(ArrayList<Question> allQuestions) { //3 times the weight of each question
		for(int i = 0; i < allQuestions.size(); i++) {
			String type = allQuestions.get(i).type;
			if(type.toUpperCase().equals("CAPITALIST (DOMESTIC)")) {
				moderateDCScore = moderateDCScore + (mean*allQuestions.get(i).weight);
				dcStD = dcStD + allQuestions.get(i).weight;
			} else if(type.toUpperCase().equals("CAPITALIST (INTERNATIONAL)")) {
				moderateICScore = moderateICScore + (mean*allQuestions.get(i).weight);
				icStD = icStD + allQuestions.get(i).weight;
			} else if(type.toUpperCase().equals("INTERVENTIONIST")) {
				moderateIntervScore = moderateIntervScore + (mean*allQuestions.get(i).weight);
				intervStD = intervStD + allQuestions.get(i).weight;
			} else if(type.toUpperCase().equals("SOCIAL")) {
				moderateSocScore = moderateSocScore + (mean*allQuestions.get(i).weight);
				socStD = socStD + allQuestions.get(i).weight;
			}
		}
	}
	
	// increment methods, add weight*answer to each score
	public void incrementScore(Question question) {
		String type = question.type;
		if(type.toUpperCase().equals("CAPITALIST (DOMESTIC)")) {
			incrementDCScore(question.weight, question.answer);
		} else if(type.toUpperCase().equals("CAPITALIST (INTERNATIONAL)")) {
			incrementICScore(question.weight, question.answer);
		} else if(type.toUpperCase().equals("INTERVENTIONIST")) {
			incrementIntervScore(question.weight, question.answer);
		} else if(type.toUpperCase().equals("SOCIAL")) {
			incrementSocScore(question.weight, question.answer);
		}
	}
	
	public void incrementDCScore(double weight, double answer) {
		domesticCapitalismScore = domesticCapitalismScore + (weight*answer);
	}
	public void incrementICScore(double weight, double answer) {
		internationalCapitalismScore = internationalCapitalismScore + (weight*answer);
	}
	public void incrementIntervScore(double weight, double answer) {
		interventionismScore = interventionismScore + (weight*answer);
	}
	public void incrementSocScore(double weight, double answer) {
		socialScore = socialScore + (weight*answer);
	}
	
	
	// uses statistics to see how abnormal the user's political beliefs are
	public void calculateAdjustedScores() {
		adjustedDCScore = (domesticCapitalismScore-moderateDCScore)/(dcStD*2);
		adjustedICScore = (internationalCapitalismScore-moderateICScore)/(icStD*2);
		adjustedIntervScore = (interventionismScore-moderateIntervScore)/(intervStD*2);
		adjustedSocScore = (socialScore-moderateSocScore)/(socStD*2);
	}
	
    public static double pdf(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    public static double pdf(double x, double mu, double sigma) {
        return pdf((x - mu) / sigma) / sigma;
    }    
}
