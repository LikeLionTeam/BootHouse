import { initCategorySelection } from './categorySelection.js';
import { initFilterSelection } from './filterSelection.js';
import { initComparisonMenu } from './comparisonMenu.js';
import { initCourseList } from './courseList.js';
import { initPagination } from './pagination.js';

document.addEventListener('DOMContentLoaded', () => {
    initCategorySelection();
    initFilterSelection();
    initComparisonMenu();
    initCourseList();
    initPagination();
});