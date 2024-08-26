import { initWishlist } from './wishlist.js';
import { initReview } from './review.js';
import { initShare } from './share.js';
import { initMap } from './map.js';

document.addEventListener('DOMContentLoaded', () => {
    initWishlist();
    initReview();
    initShare();
    initMap();
});