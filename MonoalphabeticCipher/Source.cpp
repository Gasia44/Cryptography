#include<iostream>
#include<string>
#include <algorithm>
using namespace std;


//I have 3 functions, one calculate the maximum frequency every time
// the other replace the one letter by another
//the third one, for giving more than one possible plaintext(mmake changes in the frequnecy array)

//You only need to specify how many possible plaintexts you want

//I used the ciphertext example of monoalphabetic from the textbook
//In order to specify the ciphertext, you need to put: in the main, there is string ciphertext
//you need to put what you want there

string replace(string text, char change, char by)
{
	for (int i = 0; i < text.length(); i++)
	{
		if (text[i] == change)
			text[i] = by;
	}
	return text;
}


int findmax(int count[])
{
	int max = 0;
	int indexofmax = 0;
	for (int i = 0; i < 26; i++)
	{
		if (count[i] > max)
		{
			max = count[i];
			indexofmax = i;
		}

	}
	return indexofmax;
}

void possible_plaintexts(char* frequencies, int times)
{

	if (times == 1)
	{
		swap(frequencies[0], frequencies[1]);
		swap(frequencies[2], frequencies[3]);
	}
	if (times == 2)
	{
		swap(frequencies[1], frequencies[2]);
		swap(frequencies[3], frequencies[4]);
	}
	if (times == 3)
	{
		swap(frequencies[2], frequencies[3]);
		swap(frequencies[13], frequencies[14]);

	}
	if (times == 4)
	{
		swap(frequencies[3], frequencies[4]);
		swap(frequencies[13], frequencies[14]);
		swap(frequencies[4], frequencies[6]);
	}

	if (times == 5)
	{
		swap(frequencies[4], frequencies[5]);
		swap(frequencies[8], frequencies[9]);
	}

	if (times == 6)
	{
		swap(frequencies[5], frequencies[6]);
		swap(frequencies[15], frequencies[16]);
	}

	if (times == 7)
	{
		swap(frequencies[3], frequencies[4]);
		swap(frequencies[2], frequencies[4]);
	}


	if (times >= 7)
	{
		swap(frequencies[times - 1], frequencies[times]);
		swap(frequencies[times - 2], frequencies[times - 3]);
		swap(frequencies[times], frequencies[times - 5]);
	}
}


void main()
{
	string ciphertext = "UZQSOVUOHXMOPVGPOZPEVSGZWSZOPFPESXUDBMETSXAIZVUEPHZHMDZSHZOWSFPAPPDTSVPQUZWYMXUZUHSXEPYEPOPDZSZUFPOMBZWPFUPZHMDJUDTMOHMQ";
	transform(ciphertext.begin(), ciphertext.end(), ciphertext.begin(), toupper);
	int count[26] ;

	for (int i = 0; i < 26; i++)
		count[i] = 0;

//Frequency 
	for (int i = 0; i < ciphertext.length(); i++)
	{
		count[ciphertext[i] - 'A']++;
	}


	int possibilities = 1;
	cout << "How many possibilities do you want?" << endl << "write a number: " ;
	cin >> possibilities;
	cout << endl;

	for (int times = 0; times < possibilities; times++)
	{
		char frequencies[] = { 'e','t','a','o','i','n','s','h','r','d','l','c','u','m','w','f','g','y','p','b','v','k','j','x','q','z' };

		string AdditionalCiphertext = ciphertext;
		int additionalcount[26];

		for (int i = 0; i < sizeof(count) / 4; i++)
			additionalcount[i] = count[i];

		possible_plaintexts(frequencies, times);

		for (int i = 0; i < 26; i++)
		{
			int max = findmax(additionalcount);
			AdditionalCiphertext = replace(AdditionalCiphertext, char('A' + max), frequencies[i]);
			additionalcount[max] = -1;
		}

		cout << AdditionalCiphertext << endl;
	}

	
}