import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.Recommendation;
import org.apache.mahout.cf.taste.impl.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.impl.recommender.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendationException;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;

import java.io.File;
import java.util.List;

public class RecommendationSystem{
    public static void main(String[] args) {
        try {
            // Load the data model (from ratings.csv file)
            DataModel model = new FileDataModel(new File("ratings.csv"));

            // UserSimilarity using Euclidean Distance or Tanimoto Coefficient
            UserSimilarity similarity = new EuclideanDistanceSimilarity(model);

            // Recommender (Using SlopeOne algorithm)
            Recommender recommender = new SlopeOneRecommender(model, similarity);

            // Recommend 3 items for user with userId 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            // Display the recommendations
            System.out.println("Recommendations for User 1:");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println(
                        "Item ID: " + recommendation.getItemID() + ", Estimated Rating: " + recommendation.getValue());
            }

        } catch (TasteException | RecommendationException e) {
            e.printStackTrace();
        }
    }
}
