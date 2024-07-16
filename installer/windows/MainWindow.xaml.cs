using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Threading;
using System.Drawing;
using Microsoft.Win32.SafeHandles;

namespace ZtrolixLibs_Win
{
    public partial class MainWindow : Window
    {
        private const string FABRIC_API_BASE_URL = "https://cdn.modrinth.com/data/P7dR8mSH/versions/";
        private const string QUILT_API_BASE_URL = "https://cdn.modrinth.com/data/qvIfYCYJ/versions/";
        private const string FABRIC_QUILT_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/fabric.jar";
        private const string SPIGOT_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/spigot.jar";
        private const string NEOFORGE_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/neo.jar";
        private const string FORGE_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/forge.jar";

        private Dictionary<string, List<string>> typeVersions = new Dictionary<string, List<string>>()
        {
            { "Fabric", new List<string> { "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5 EXPERIMENTAL", "1.20.6 EXPERIMENTAL" } },
            { "Quilt", new List<string> { "1.20", "1.20.1", "1.20.2", "1.20.4", "1.20.6 EXPERIMENTAL" } },
            { "Forge", new List<string> { "1.20.1" } },
            { "NeoForge", new List<string> { "1.20.4" } },
            { "Spigot", new List<string> { "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4" } }
        };

        public MainWindow()
        {
            InitializeComponent();
            TypeComboBox.ItemsSource = typeVersions.Keys;
            TypeComboBox.SelectedItem = "Fabric";
        }

        private void TypeComboBox_SelectionChanged(object sender, System.Windows.Controls.SelectionChangedEventArgs e)
        {
            string selectedType = TypeComboBox.SelectedItem as string;
            VersionComboBox.ItemsSource = typeVersions[selectedType];
            VersionComboBox.SelectedIndex = 0;
        }

        private async void DownloadButton_Click(object sender, RoutedEventArgs e)
        {
            string selectedType = TypeComboBox.SelectedItem as string;
            string selectedVersion = VersionComboBox.SelectedItem as string;

            if (string.IsNullOrEmpty(selectedType) || string.IsNullOrEmpty(selectedVersion))
            {
                MessageBox.Show("Please select both type and version.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            var dialog = new SaveFileDialog
            {
                Title = "Select Folder",
                Filter = "Folder|.",
                FileName = "Save Inside the Folder to Install To ( DO NOT SAVE INSIDE MODS OR PLUGIN FOLDER )"
            };

            if (dialog.ShowDialog() == true)
            {
                string folderPath = System.IO.Path.GetDirectoryName(dialog.FileName);
                string targetFolder = selectedType == "Spigot" ? "plugins" : "mods";
                string downloadPath = System.IO.Path.Combine(folderPath, targetFolder);

                Directory.CreateDirectory(downloadPath);

                var downloads = GetDownloadUrls(selectedType, selectedVersion);
                DownloadProgressBarBorder.Visibility = Visibility.Visible;

                foreach (var download in downloads)
                {
                    string filePath = System.IO.Path.Combine(downloadPath, download.customName);
                    await DownloadFileAsync(download.url, filePath);
                }

                DownloadProgressBarBorder.Visibility = Visibility.Collapsed;

                ShowImageForThreeSeconds();
                MessageBox.Show("Download complete!", "ZtrolixLibs Installer", MessageBoxButton.OK, MessageBoxImage.Information);
            }
        }

        private List<(string url, string customName)> GetDownloadUrls(string type, string version)
        {
            var urls = new List<(string url, string customName)>();

            switch (type)
            {
                case "Fabric":
                    urls.Add((GetFabricUrl(version), $"ZtrolixLibs-FabricAPI-{version}.jar"));
                    urls.Add((FABRIC_QUILT_URL, "ZtrolixLibs-Fabric.jar"));
                    break;
                case "Quilt":
                    urls.Add((GetQuiltUrl(version), $"ZtrolixLibs-QuiltedFabricAPI-{version}.jar"));
                    urls.Add((FABRIC_QUILT_URL, "ZtrolixLibs-Quilt.jar"));
                    break;
                case "Forge":
                    urls.Add((FORGE_URL, "ZtrolixLibs-Forge.jar"));
                    break;
                case "NeoForge":
                    urls.Add((NEOFORGE_URL, "ZtrolixLibs-NeoForge.jar"));
                    break;
                case "Spigot":
                    urls.Add((SPIGOT_URL, "ZtrolixLibs-Spigot.jar"));
                    break;
            }

            return urls;
        }

        private string GetFabricUrl(string version)
        {
            switch (version)
            {
                case "1.20": return FABRIC_API_BASE_URL + "n2c5lxAo/fabric-api-0.83.0%2B1.20.jar";
                case "1.20.1": return FABRIC_API_BASE_URL + "P7uGFii0/fabric-api-0.92.2%2B1.20.1.jar";
                case "1.20.2": return FABRIC_API_BASE_URL + "8GVp7wDk/fabric-api-0.91.6%2B1.20.2.jar";
                case "1.20.3": return FABRIC_API_BASE_URL + "Yolngp3s/fabric-api-0.91.1%2B1.20.3.jar";
                case "1.20.4": return FABRIC_API_BASE_URL + "tAwdMmKY/fabric-api-0.97.1%2B1.20.4.jar";
                case "1.20.5 EXPERIMENTAL": return FABRIC_API_BASE_URL + "GCdY4I8I/fabric-api-0.97.8%2B1.20.5.jar";
                case "1.20.6 EXPERIMENTAL": return FABRIC_API_BASE_URL + "GT0R5Mz7/fabric-api-0.100.4%2B1.20.6.jar";
                default: throw new Exception("Invalid Fabric version");
            }
        }

        private string GetQuiltUrl(string version)
        {
            switch (version)
            {
                case "1.20": return QUILT_API_BASE_URL + "vTQynnGn/qfapi-7.2.2_qsl-6.1.2_fapi-0.88.1_mc-1.20.1.jar";
                case "1.20.1": return QUILT_API_BASE_URL + "Gydw2vxY/qfapi-7.5.0_qsl-6.1.2_fapi-0.92.2_mc-1.20.1.jar";
                case "1.20.2": return QUILT_API_BASE_URL + "zHVlrS0A/quilted-fabric-api-8.0.0-alpha.6%2B0.91.6-1.20.2.jar";
                case "1.20.4": return QUILT_API_BASE_URL + "AljqyvST/quilted-fabric-api-9.0.0-alpha.8%2B0.97.0-1.20.4.jar";
                case "1.20.6 EXPERIMENTAL": return QUILT_API_BASE_URL + "IQQyeJNR/quilted-fabric-api-10.0.0-alpha.3%2B0.100.4-1.20.6.jar";
                default: throw new Exception("Invalid Quilt version");
            }
        }

        private async Task DownloadFileAsync(string url, string destinationPath)
        {
            using (HttpClient client = new HttpClient())
            {
                using (HttpResponseMessage response = await client.GetAsync(url, HttpCompletionOption.ResponseHeadersRead))
                {
                    response.EnsureSuccessStatusCode();
                    var totalBytes = response.Content.Headers.ContentLength.HasValue ? response.Content.Headers.ContentLength.Value : -1L;
                    var canReportProgress = totalBytes != -1;

                    using (var contentStream = await response.Content.ReadAsStreamAsync())
                    using (var fileStream = new FileStream(destinationPath, FileMode.Create, FileAccess.Write, FileShare.None, 8192, true))
                    {
                        var totalRead = 0L;
                        var buffer = new byte[8192];
                        var isMoreToRead = true;

                        do
                        {
                            var read = await contentStream.ReadAsync(buffer, 0, buffer.Length);
                            if (read == 0)
                            {
                                isMoreToRead = false;
                                continue;
                            }

                            await fileStream.WriteAsync(buffer, 0, read);
                            totalRead += read;

                            if (canReportProgress)
                            {
                                DownloadProgressBar.Value = (totalRead * 1d) / (totalBytes * 1d) * 100;
                            }
                        }
                        while (isMoreToRead);
                    }
                }
            }
        }

        private void MoveWindow(object sender, RoutedEventArgs e)
        {
            DragMove();
        }

        private void Close(object sender, RoutedEventArgs e)
        {
            Close();
        }

        private void Minimize(object sender, RoutedEventArgs e)
        {
            WindowState = WindowState.Minimized;
        }

        private void ShowImageForThreeSeconds()
        {
            Downloaded.Visibility = Visibility.Visible;

            DispatcherTimer timer = new DispatcherTimer();
            timer.Interval = TimeSpan.FromSeconds(5);
            timer.Tick += Timer_Tick;
            timer.Start();
        }

        private void Timer_Tick(object sender, EventArgs e)
        {
            Downloaded.Visibility = Visibility.Collapsed;

            DispatcherTimer timer = (DispatcherTimer)sender;
            timer.Stop();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            Cursor customCursor = new Cursor(Application.GetResourceStream(new Uri("pack://application:,,,/cursor2.cur")).Stream);
            //this.Cursor = customCursor;
        }
    }

    public class SafeIconHandle : Microsoft.Win32.SafeHandles.SafeHandleZeroOrMinusOneIsInvalid
    {
        public SafeIconHandle(IntPtr preexistingHandle, bool ownsHandle = true)
            : base(ownsHandle)
        {
            SetHandle(preexistingHandle);
        }

        protected override bool ReleaseHandle()
        {
            return DestroyIcon(this.handle);
        }

        [System.Runtime.InteropServices.DllImport("user32.dll", SetLastError = true)]
        [return: System.Runtime.InteropServices.MarshalAs(System.Runtime.InteropServices.UnmanagedType.Bool)]
        private static extern bool DestroyIcon(IntPtr hIcon);
    }
}
