import java.util.*;

/**
 * PROJECT: AI-Based Recommendation System
 * TASK 4: Suggest products based on User-User Collaborative Filtering.
 * DELIVERABLE: A working recommendation engine with sample dataset.
 */
public class RecommendationSystem {

    // Sample Data: User -> (Product, Rating)
    private static Map<String, Map<String, Integer>> userRatings = new HashMap<>();

    public static void main(String[] args) {
        seedData();
        
        String targetUser = "Ayush";
        System.out.println("--- AI Recommendation Engine for: " + targetUser + " ---");
        
        List<String> recommendations = getRecommendations(targetUser);
        
        if (recommendations.isEmpty()) {
            System.out.println("No new recommendations. Try rating more products!");
        } else {
            System.out.println("Top Recommended Products for you:");
            for (String product : recommendations) {
                System.out.println("⭐ " + product);
            }
        }
    }

    // AI Logic: Find users with similar tastes and suggest what they liked
    public static List<String> getRecommendations(String user) {
        Map<String, Integer> targetRatings = userRatings.get(user);
        List<String> suggestions = new ArrayList<>();
        
        for (String otherUser : userRatings.keySet()) {
            if (otherUser.equals(user)) continue;

            // Simple Similarity Check: If they both liked the same thing
            Map<String, Integer> otherRatings = userRatings.get(otherUser);
            for (String product : otherRatings.keySet()) {
                // Agar target user ne ye product nahi dekha aur other user ne ise 4+ rating di hai
                if (!targetRatings.containsKey(product) && otherRatings.get(product) >= 4) {
                    if (!suggestions.contains(product)) {
                        suggestions.add(product);
                    }
                }
            }
        }
        return suggestions;
    }

    // Mock Database
    private static void seedData() {
        // Ayush likes Java and IoT
        Map<String, Integer> r1 = new HashMap<>();
        r1.put("Java Course", 5);
        r1.put("IoT Kit", 4);
        userRatings.put("Ayush", r1);

        // User B likes Java, IoT, and Cloud Computing
        Map<String, Integer> r2 = new HashMap<>();
        r2.put("Java Course", 5);
        r2.put("IoT Kit", 5);
        r2.put("Cloud Computing", 5); // New for Ayush
        userRatings.put("User_B", r2);

        // User C likes Python and AI
        Map<String, Integer> r3 = new HashMap<>();
        r3.put("Python Course", 5);
        r3.put("AI Basics", 4);
        userRatings.put("User_C", r3);
    }
}
