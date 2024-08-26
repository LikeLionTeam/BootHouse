export function initReview() {
    const reviews = document.querySelectorAll(".course-review-wrap .review-list .review-item");
    const reviewMoreBtn = document.querySelector(".review-more");

    if (reviews.length > 3) {
        reviews.forEach((review, index) => {
            if (index >= 3) review.classList.add("hidden");
        });

        reviewMoreBtn.classList.remove("hidden");

        reviewMoreBtn.addEventListener("click", function() {
            if (this.textContent === "리뷰 더보기") {
                reviews.forEach(review => review.classList.remove("hidden"));
                this.textContent = "리뷰 접기";
            } else {
                reviews.forEach((review, index) => {
                    if (index >= 3) review.classList.add("hidden");
                });
                this.textContent = "리뷰 더보기";
            }
        });
    }
}