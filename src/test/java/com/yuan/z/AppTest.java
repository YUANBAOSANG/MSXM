package com.yuan.z;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.PriorityQueue;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        Solution sou = new Solution();
        int ans =sou.findKthLargest(new int[]{3,2,1,5,6,4},2);
        System.out.println();
    }
}
class Solution {
    public int findKthLargest(int[] nums, int k) {
        return quck(nums,0,nums.length-1,k);
    }
    private int quck(int[] nums,int left,int right,int target){
        if(left==right) return nums[left];
        int l = left,r =right,mid = nums[left+right>>1];;
        while(left<right){
            while(nums[left]>mid) left++;
            while(nums[right]<mid)right--;
            if(left<right){
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }
        if(right>=target-1)return quck(nums,l,right,target);
        return quck(nums,left,r,target);
    }
}