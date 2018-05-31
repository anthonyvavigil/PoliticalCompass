# PoliticalCompass
A text-based political compass made in java, using real-world examples.

# Calculation of Scores
The user's score for each category is run through a normal probability distribution function, to calculate the percentage of users with the same political views. The mean for each question assumes the user chose "3" for each question. The standard deviation of the mean is 1 point, meaning the data will be normally distributed around 3. This was done to make it harder to reach extreme political views, better reflecting real-world politics
