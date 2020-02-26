using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace SwapEdgeIDs
{
    class Program
    {
        private static readonly string nodeIDSeparator = "_";
        private static readonly Regex regex = new Regex(@"(?<edgeID>(?<leftNode>[^,_]+)_(?<rightNode>[^,]+))(?<remainder>\,.*)");

        static void Main(string[] args)
        {
            string path = PromptForFilePath("Enter a valid file path: ", "File does not exist.");
            var result = File.ReadAllLines(path)
                .Select(line => OrderEdgeIDsOrdinally(line));
            string outputPath = Path.Combine(
                Path.GetDirectoryName(path),
                $"{Path.GetFileNameWithoutExtension(path)}-fixed{Path.GetExtension(path)}");

            foreach (string line in result) Console.WriteLine(line);

            File.WriteAllLines(outputPath, result.ToArray());

            Console.ReadLine();
        }

        private static string PromptForFilePath(string prompt, string error)
        {
            while (true)
            {
                Console.WriteLine(prompt);
                string path = Console.ReadLine();
                if (File.Exists(path))
                    return path;
                Console.WriteLine(error);
            }
        }

        private static string OrderEdgeIDsOrdinally(string line)
        {
            Match match = regex.Match(line);
            string leftNode = match.Groups["leftNode"].Value;
            string rightNode = match.Groups["rightNode"].Value;
            string remainder = match.Groups["remainder"].Value;
            string lowest = Lowest(leftNode, rightNode);
            string highest = Highest(leftNode, rightNode);

            return $"{lowest}{nodeIDSeparator}{highest}{remainder}";
        }

        private static string Lowest(string left, string right)
        {
            return string.CompareOrdinal(left, right) < 0 ?
                left : right;
        }
        private static string Highest(string left, string right)
        {
            return string.CompareOrdinal(left, right) >= 0 ?
                left : right;
        }
    }
}
