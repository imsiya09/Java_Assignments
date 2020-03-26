
/*
 * Author :Snehal Patil,Nagpur
 * Email  :snehalpatil2011@gmail.com
 * 
 * Disclaimer :The projects questions does not have any resemblance
 *  			with the actual exam.Though they are on the same pattern 
 *  			that I have experienced in practice assignmets.It contains the ".rtf file" 
 *  			which describes the problem statement .
 *  
 *  In case of violation of Any privacy or rights please inform
 *  
 *  
 *                "Practice makes a man perfect.................... and women too"
 */



package com.psl.main;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.psl.bean.Student;
import com.psl.bean.Subject;
import com.psl.util.StudentAssignmentImpl;

public class Client {


	public static void main(String[] args) 
	{
		
		StudentAssignmentImpl impl=new StudentAssignmentImpl();
		Map<Subject, List<Student>> map=impl.populateData("subject.txt","student.txt");
		
		System.out.println(map);//first method
		
		System.out.println("\n\n");
		
		impl.calculateMarks(map);//second method
		
		System.out.println("\n\n");

		impl.offerGraseMarks(map);//fourth method
		
		System.out.println("\n\n");

		//third method
		Set<Map.Entry<Subject,List<Student>>> set=map.entrySet();
		for (Entry<Subject, List<Student>> entry : set) 
		{
			System.out.println("..................."+entry.getKey().getSubjectName());
			for(Student student:entry.getValue())
			{
				System.out.println(student.getSubject()+"\t"+student.getMarksObtained()+"\t"+student.getNumberOfDays()+"\t"+student.isDefaulter());
			}
		}
		
		
	}
}